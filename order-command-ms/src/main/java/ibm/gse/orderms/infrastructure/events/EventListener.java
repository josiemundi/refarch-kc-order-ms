package ibm.gse.orderms.infrastructure.events;

public interface EventListener {

    public void handle(OrderEventAbstract event);

}
