package io.github.picodotdev.blogbitix.springbootjaxrs;

public class DefaultMessageService implements MessageService {

    @Override
    public Message create(String message) {
        return new Message(message);
    }
}
