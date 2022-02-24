package games.mythical.saga.sdk.client.executor;

public interface SagaTemplateExecutor {
    // make sure to create the necessary response event stream
    void updateEventOfSomeObject(String someEvent) throws Exception;
}
