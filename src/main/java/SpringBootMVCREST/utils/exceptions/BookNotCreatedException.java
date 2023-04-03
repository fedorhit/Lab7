package SpringBootMVCREST.utils.exceptions;

public class BookNotCreatedException extends RuntimeException{
    public BookNotCreatedException(String msg) {
        super(msg);
    }
}
