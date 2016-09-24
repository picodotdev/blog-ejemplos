package io.github.picodotdev.blogbitix.springbootjaxrsoauth;

public class DefaultMessageService implements MessageService {

    @Override
    public Message create(String message) {
        return new Message(message);
    }
}
