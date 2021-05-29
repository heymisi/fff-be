package hu.fitforfun.exception;

public class Response {

    private Object payload;
    private ResponseType type;

    public Response() {
    }

    public Response(Object payload, ResponseType type) {
        this.payload = payload;
        this.type = type;
    }


    public static Response createErrorResponse(Object payload) {
        return new Response(payload, ResponseType.ERROR);
    }

    public static Response createOKResponse(Object payload) {
        return new Response(payload, ResponseType.OK);
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }


}

