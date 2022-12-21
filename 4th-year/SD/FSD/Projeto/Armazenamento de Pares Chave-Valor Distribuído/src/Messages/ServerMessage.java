package Messages;

import java.io.Serializable;

public class ServerMessage implements Serializable
{
    private Object obj;
    private Integer id;
    private Integer timestamp;
    private Integer port;
    private String payload;


    public ServerMessage(Object o, int id, int timestamp)
    {
        this.obj = o;
        this.id = id;
        this.timestamp = timestamp;
    }

    public ServerMessage(int prt, int timestamp, String pld)
    {
        this.port = prt;
        this.timestamp = timestamp;
        this.payload = pld;
    }

    public Integer getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp)
    {
        this.timestamp = timestamp;
    }

    public Object getObj()
    {
        return this.obj;
    }

    public void setObj(Object o)
    {
        this.obj = o;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPort() {
        return port;
    }

    public String getPayload() {
        return payload;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
