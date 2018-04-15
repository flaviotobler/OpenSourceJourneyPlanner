package org.opentripplanner.csa;

import java.util.HashMap;
import java.util.Map;

import org.onebusaway.csv_entities.EntityHandler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TimeTableEntityCounter implements EntityHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(TimeTableEntityCounter.class);
    
    private Map<Class<?>, Integer> _count = new HashMap<Class<?>, Integer>();

    @Override
    public void handleEntity(Object bean) {
        int count = incrementCount(bean.getClass());
        if (count % 1000000 == 0)
            if (LOG.isDebugEnabled()) {
                String name = bean.getClass().getName();
                int index = name.lastIndexOf('.');
                if (index != -1)
                    name = name.substring(index + 1);
                LOG.debug("loading " + name + ": " + count);
            }
    }

    private int incrementCount(Class<?> entityType) {
        Integer value = _count.get(entityType);
        if (value == null)
            value = 0;
        value++;
        _count.put(entityType, value);
        return value;
    }

}