package io.github.picodotdev.blogbitix.log4j;

import org.apache.logging.log4j.message.Message;

public class SimpleProductMessage implements Message {

    private Product product;

    public SimpleProductMessage(Product product) {
        this.product = product;
    }

    @Override
    public String getFormat() {
        return "Product(%d, %s)";
    }

    @Override
    public Object[] getParameters() {
        return new Object[] { product.getId(), product.getName() };
    }

    @Override
    public String getFormattedMessage() {
        return String.format(getFormat(), getParameters());
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }
}
