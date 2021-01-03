package common.core.app.cache;

import java.io.Serializable;

public final class NullObject implements Serializable {

    private static final long serialVersionUID = -3119666019500654857L;

    public static final NullObject INSTANCE = new NullObject();

    private NullObject() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(serialVersionUID).intValue();
    }

}
