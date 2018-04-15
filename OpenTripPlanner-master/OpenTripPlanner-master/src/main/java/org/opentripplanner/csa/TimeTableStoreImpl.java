package org.opentripplanner.csa;

import java.io.Serializable;
import java.util.Collection;

import org.onebusaway.gtfs.model.IdentityBean;
import org.onebusaway.gtfs.services.GenericMutableDao;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;

public class TimeTableStoreImpl implements GenericMutableDao {

    private GtfsMutableRelationalDao dao;

    TimeTableStoreImpl(GtfsMutableRelationalDao dao) {
        this.dao = dao;
    }

    @Override
    public void open() {
        dao.open();
    }

    @Override
    public <T> T getEntityForId(Class<T> type, Serializable id) {
        return dao.getEntityForId(type, id);
    }

    @Override
    public void saveEntity(Object entity) {
        dao.saveEntity(entity);
    }

    @Override
    public void flush() {
        dao.flush();
    }

    @Override
    public void close() {
        dao.close();
    }

    @Override
    public <T> void clearAllEntitiesForType(Class<T> type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K extends Serializable, T extends IdentityBean<K>> void removeEntity(T entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Collection<T> getAllEntitiesForType(Class<T> type) {
        return dao.getAllEntitiesForType(type);
    }

    @Override
    public void saveOrUpdateEntity(Object entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateEntity(Object entity) {
        throw new UnsupportedOperationException();
    }
}