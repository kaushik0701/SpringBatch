package org.hrptech;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/mobile")
public class MobileResource {

    List<Mobile> mobileList = new ArrayList<>();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMobileList(){
        return Response.ok(mobileList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMobile(Mobile mobile){
        mobileList.add(mobile);
        return Response.ok(mobile).build();
    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMobile(@PathParam("id")int id, Mobile mobileToUpdate){
        mobileList = mobileList.stream().map(mobile->{
            if(mobile.getId()==id){
                return mobileToUpdate;
            }else{
                return mobile;
            }
        }).collect(Collectors.toList());
        return Response.ok(mobileList).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMobile(@PathParam("id") int mobileIdToDelete) {
        Optional<Mobile> mobileToDelete = mobileList.stream().filter(mobile -> mobile.getId() == mobileIdToDelete).findFirst();
        if (mobileToDelete.isPresent()) {
            mobileList.remove(mobileToDelete.get());
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMobileById(@PathParam("id") int id) {
        Optional<Mobile> optionalMobile = mobileList.stream().filter(mobile -> mobile.getId() == id).findFirst();
        if (optionalMobile.isPresent()) {
            return Response.ok(optionalMobile.get()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
//http://localhost:8080/q/swagger-ui/