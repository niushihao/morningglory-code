package com.morningglory.basic.annotation;

import com.morningglory.basic.exception.ParameterException;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValue.Validator.class)
public @interface EnumValue {

    String message() default "{custom.value.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    String enumMethod();

    class Validator implements ConstraintValidator<EnumValue,Object>{

        private Class<? extends Enum<?>> enumClass;
        private String enumMethod;

        @Override
        public void initialize(EnumValue enumValue) {
            enumMethod = enumValue.enumMethod();
            enumClass = enumValue.enumClass();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

            if(value == null){
                return Boolean.TRUE;
            }

            if(enumClass == null || enumMethod == null){
                return Boolean.TRUE;
            }

            Class<?> valueClass = value.getClass();

            try{
                Method method = enumClass.getMethod(enumMethod,valueClass);
                if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                    throw new ParameterException("哈哈1");
                }

                if(!Modifier.isStatic(method.getModifiers())) {
                    throw new ParameterException("哈哈2");
                }

                Boolean result = (Boolean)method.invoke(null, value);
                return result == null ? false : result;
            }catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ParameterException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
