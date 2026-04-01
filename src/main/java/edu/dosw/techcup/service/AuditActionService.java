package edu.dosw.techcup.service;

import edu.dosw.techcup.model.audit.AuditAction;

import java.util.LinkedHashMap;
import java.util.Map;

public class AuditActionService {

    //Attributes
    private Map<Long, AuditAction> logs = new LinkedHashMap<Long, AuditAction>();

    //Methods
    public void registerAuditAction(AuditAction a) {
        if (a != null) logs.put(a.getId(), a);
    }

    public Map<Long, AuditAction> getAuditList() {
        return logs;
    }

    public int getAuditListSize() {
        return logs.size();
    }

    public AuditAction getLastRegister() {
        return logs.values().stream().reduce((first, second) -> second).orElse(null);
    }

    public AuditAction getAuditAction(long id) {
        return logs.containsKey(id) ? logs.get(id) : null;
    }
}
