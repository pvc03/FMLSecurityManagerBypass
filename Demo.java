package me.trdyun.demo;

import me.trdyun.fml.DummyFMLSecurityManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static java.lang.System.out;

public class Demo {
    public static synchronized void main(String[] args) throws Exception {
        System.setSecurityManager(new DummyFMLSecurityManager());
        bypassFML();
        System.setSecurityManager(new HaloSecurityManager());
    }
    private static void bypassFML(){
        Field securityField = findSecurity();
        try {
            securityField.set(null,null);
            out.println("SECURITYMANAGER ATTACKED");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static Field findSecurity(){
        try{
            Method targetMethod = System.class.getClass().getDeclaredMethod("getDeclaredFields0",boolean.class);
            targetMethod.setAccessible(true);
            Field[] fields = (Field[]) targetMethod.invoke(System.class,false);
            for(Field field : fields){
                if(field.getName().equals("security")){
                    field.setAccessible(true);
                    return field;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
