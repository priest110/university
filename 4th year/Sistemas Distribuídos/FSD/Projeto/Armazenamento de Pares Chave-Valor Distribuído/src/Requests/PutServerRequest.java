package Requests;

import io.atomix.utils.net.Address;

import java.util.Map;

public class PutServerRequest
{
    private int inTransit;
    private int total;
    private int id;
    private int msg_cl_id;
    public Map<Address, Map<Long,byte[]>> put_req;
    private Address sender;

    public PutServerRequest(Map<Address, Map<Long,byte[]>> put_req, Address s, Integer i)
    {
        this.sender = s;
        this.id = -1;
        this.put_req = put_req;
        this.inTransit = this.total = put_req.size();
        this.msg_cl_id = i;
    }

    public int getMsg_cl_id() {
        return msg_cl_id;
    }

    public void setMsg_cl_id(int msg_cl_id) {
        this.msg_cl_id = msg_cl_id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public boolean isFinished()
    {
        return this.inTransit == 0;
    }

    public void recievedOne()
    {
        if(this.inTransit > 0) this.inTransit--;
    }

    public Address getSender()
    {
        return this.sender;
    }
}
