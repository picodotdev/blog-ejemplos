package io.github.picodotdev.blogbitix.springbootjaxrsoauth.server;

public class DefaultMessageService implements MessageService {

    @Override
    public Message create(String message) {
        return new Message(message);
    }
}
