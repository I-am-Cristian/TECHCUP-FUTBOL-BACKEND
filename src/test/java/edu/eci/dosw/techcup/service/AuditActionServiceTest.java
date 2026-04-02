package edu.eci.dosw.techcup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.eci.dosw.techcup.entity.Action;
import edu.eci.dosw.techcup.entity.AuditAction;
import edu.eci.dosw.techcup.entity.Manager;

public class AuditActionServiceTest {

    private AuditActionService auditActionService;

    
    private static final Manager USER_A = new Manager(1L, "usera@escuelaing.edu.co", "pass");
    private static final Manager USER_B = new Manager(2L, "userb@gmail.com", "pass");
    private static final Manager USER_C = new Manager(3L, "userc@escuelaing.edu.co", "pass");

    @BeforeEach
    void setUp() {
        auditActionService = new AuditActionService();
    }

    @Test
    public void shouldRegisterFirstAction() {
        //Having
        AuditAction firstAction = new AuditAction(100010010, USER_A, Action.LOGIN);
        firstAction.setDetails("Esto es una prueba y el usuario utilizado es un mock");

        //When
        auditActionService.registerAuditAction(firstAction);

        //Then
        assertEquals(1, auditActionService.getAuditListSize());
        assertEquals(firstAction, auditActionService.getLastRegister());
    }

    @Test
    public void shouldRegisterAction() {
        //Having
        AuditAction action1 = new AuditAction(56565656, USER_A, Action.CHANGE_PAYMENT_STATE);
        AuditAction action2 = new AuditAction(1234567, USER_B, Action.LOGOUT);
        auditActionService.registerAuditAction(action1);
        auditActionService.registerAuditAction(action2);
        AuditAction newAction = new AuditAction(77777777, USER_C, Action.MATCH_RESULT_CHANGE);

        //When
        auditActionService.registerAuditAction(newAction);

        //Then
        assertEquals(3, auditActionService.getAuditListSize());
        assertEquals(newAction, auditActionService.getLastRegister());
    }

    @Test
    public void shouldNotRegisterInvalidUser() {
        //Having
        AuditAction invalidAction = null;

        //When
        auditActionService.registerAuditAction(invalidAction);

        //Then
        assertEquals(0, auditActionService.getAuditListSize());
    }

    @Test
    public void shouldNotRegisterDuplicatedId() {
        //Having
        AuditAction action1 = new AuditAction(11223344, USER_A, Action.CHANGE_PAYMENT_STATE);
        AuditAction action2 = new AuditAction(11223344, USER_B, Action.LOGOUT);
        auditActionService.registerAuditAction(action1);

        //When
        auditActionService.registerAuditAction(action2);

        //Then
        assertEquals(1, auditActionService.getAuditListSize());
    }

    @Test
    public void shouldReturnEmptyLogs() {
        //Having / When
        auditActionService.getAuditList();

        //Then
        assertEquals(0, auditActionService.getAuditListSize());
    }

    @Test
    public void shouldReturnLogs() {
        //Having
        AuditAction action1 = new AuditAction(11111111, USER_A, Action.LOGIN);
        AuditAction action2 = new AuditAction(22222222, USER_B, Action.CHANGE_PAYMENT_STATE);
        AuditAction action3 = new AuditAction(33333333, USER_C, Action.MATCH_RESULT_CHANGE);
        AuditAction action4 = new AuditAction(44444444, USER_A, Action.LOGOUT);
        auditActionService.registerAuditAction(action1);
        auditActionService.registerAuditAction(action2);
        auditActionService.registerAuditAction(action3);
        auditActionService.registerAuditAction(action4);

        //When
        auditActionService.getAuditList();

        //Then
        assertEquals(4, auditActionService.getAuditListSize());
        assertEquals(action3, auditActionService.getAuditAction(33333333));
        assertEquals(action4, auditActionService.getAuditAction(44444444));
        assertEquals(action2, auditActionService.getAuditAction(22222222));
        assertEquals(action1, auditActionService.getAuditAction(11111111));
    }
}
