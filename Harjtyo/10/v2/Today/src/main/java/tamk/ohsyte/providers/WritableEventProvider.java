package tamk.ohsyte.providers;

import tamk.ohsyte.datamodel.Event;

public interface WritableEventProvider extends EventProvider {
    boolean addEvent(Event event);
}