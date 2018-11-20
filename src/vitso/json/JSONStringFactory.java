package vitso.json;

import flexjson.JSONSerializer;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;

import java.lang.reflect.Type;

public class JSONStringFactory implements ObjectFactory {
    @Override
    public Object instantiate(ObjectBinder objectBinder, Object o, Type type, Class aClass) {
        return new JSONSerializer().deepSerialize(o);
    }
}