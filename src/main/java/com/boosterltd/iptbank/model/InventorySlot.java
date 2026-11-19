package com.boosterltd.iptbank.model;

import static com.boosterltd.lib.string.NamingConventionMethods.changeNamingConvention;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

import com.boosterltd.lib.exception.HandmadeIllegalArgException;
import com.boosterltd.lib.string.util.NamingConvention;

import lombok.NonNull;

public interface InventorySlot<T> {
    public static interface BoundInventorySlot<T> {
        public T getOwnerObject();
        public void setAmount(int amount);
        public int getAmount();
        public int changeAmount(int delta);
    }

    public static <T> InventorySlot<T> of(
        @NonNull Class<T> ownerClass, @NonNull String name
    ) {
        final String nameInPascalCase = changeNamingConvention(name, NamingConvention.SCREAMING_SNAKE_CASE, NamingConvention.PASCAL_CASE);
        final String setterMethodName = "set" + nameInPascalCase + "Amount";
        final String getterMethodName = "g" + setterMethodName.substring(1, setterMethodName.length());
        final MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        
        try {
                // Find the methods.
            final MethodHandle rawSetterMethod = lookup.findVirtual(
                ownerClass, setterMethodName, MethodType.methodType(void.class, byte.class)
            );
            final MethodHandle rawGetterMethod = lookup.findVirtual(
                ownerClass, getterMethodName, MethodType.methodType(byte.class)
            );
                // Adapt casting from <byte> to <int> on the methods.
            final MethodHandle castedSetterMethod = MethodHandles.explicitCastArguments(
                rawSetterMethod, MethodType.methodType(void.class, ownerClass, int.class)
            );
            final MethodHandle castedGetterMethod = MethodHandles.explicitCastArguments(
                rawGetterMethod, MethodType.methodType(int.class, ownerClass)
            );

            return new InventorySlot<T>() {
                @Override public Class<T> getOwnerClass() {
                    return ownerClass;
                }
                @Override public void setAmount(@NonNull T ownerObject, int amount) {
                    try { castedSetterMethod.invoke(ownerObject, amount); }
                    catch (Throwable t) { throw new IllegalStateException(t); }
                }
                @Override public int getAmount(@NonNull T ownerObject) {
                    try { return (int) castedGetterMethod.invoke(ownerObject); }
                    catch (Throwable t) { throw new IllegalStateException(t); }
                }
            };
        }
        catch (NoSuchMethodException | IllegalAccessException e) {
            throw new HandmadeIllegalArgException(
                "String", "name", name,
                "The setter method or the getter method of this item doesn't exist.", e
            );
        }
    }
    public static <T> InventorySlot<T> ofProvided(
        @NonNull Class<T> ownerClass,
        @NonNull ObjIntConsumer<T> setterMethod, @NonNull ToIntFunction<T> getterMethod
    ) {
        return new InventorySlot<T>() {
            @Override public Class<T> getOwnerClass() {
                return ownerClass;
            }
            @Override public void setAmount(@NonNull T ownerObject, int amount) {
                setterMethod.accept(ownerObject, amount);
            }
            @Override public int getAmount(@NonNull T ownerObject) {
                return getterMethod.applyAsInt(ownerObject);
            }
        };
    }

    public Class<T> getOwnerClass();
    public void setAmount(@NonNull T owner, int amount);
    public int getAmount(@NonNull T owner);
    default public int changeAmount(@NonNull T owner, int delta) {
        int newAmount = getAmount(owner) + delta;
        setAmount(owner, newAmount);
        return newAmount;
    };
    default public BoundInventorySlot<T> getBound(@NonNull T ownerObject) {
        return new BoundInventorySlot<>() {
            @Override public T getOwnerObject() {
                return ownerObject;
            }
            @Override public void setAmount(int amount) {
                InventorySlot.this.setAmount(ownerObject, amount);
            }
            @Override public int getAmount() {
                return InventorySlot.this.getAmount(ownerObject);
            }
            @Override public int changeAmount(int delta) {
                return InventorySlot.this.changeAmount(ownerObject, delta);
            }
        };
    }
    default public BoundInventorySlot<T> getBoundInRuntime(@NonNull Object ownerObject) {
        T qualifiedOwnerObject;
        try {
            qualifiedOwnerObject = getOwnerClass().cast(ownerObject);
        }
        catch (ClassCastException e) {
            throw new HandmadeIllegalArgException(
                "Object", "ownerObject", ownerObject, e
            );
        }
        return getBound(qualifiedOwnerObject);
    }
}
