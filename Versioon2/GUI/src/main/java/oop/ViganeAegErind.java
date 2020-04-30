package oop;

public class ViganeAegErind extends RuntimeException{
    public ViganeAegErind(String message) {
        super("Häkker! Negatiivne aeg. "+message);
    }
}
