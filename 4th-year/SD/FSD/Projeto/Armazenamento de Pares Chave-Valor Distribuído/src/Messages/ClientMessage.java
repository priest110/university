package Messages;

import java.io.Serializable;

public class ClientMessage implements Serializable
{
    private Object obj;
    private Integer id;

    public ClientMessage(Object o, int id)
    {
        this.obj = o;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getObj()
    {
        return this.obj;
    }

    public void setObj(Object o)
    {
        this.obj = o;
    }
}
