package Requests;

import io.atomix.utils.net.Address;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GetServerRequest
{
    private int inTransit;
    private int total;
    private int id;
    private int cl_msg_id;
    private Address sender;
    public Map<Address, Collection<Long>> get_req;
    public Map<Long, byte[]> get_res;

    public GetServerRequest(Map<Address, Collection<Long>> get_req, Address s, int cmi)
    {
        this.sender = s;
        this.id = -1;
        this.get_req = get_req;
        this.get_res = new HashMap<>();
        this.inTransit = this.total = get_req.size();
        this.cl_msg_id = cmi;
    }

    public int getCl_msg_id() {
        return cl_msg_id;
    }

    public void setCl_msg_id(int cl_msg_id) {
        this.cl_msg_id = cl_msg_id;
    }

    public boolean isFinished()
    {
        return this.inTransit == 0;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void recievedOne()
    {
        if(this.inTransit > 0) this.inTransit--;
    }

    public void addToResponse(Map<Long, byte[]> res)
    {
        this.get_res.putAll(res);
        this.recievedOne();
    }

    public Address getSender()
    {
        return this.sender;
    }
}
