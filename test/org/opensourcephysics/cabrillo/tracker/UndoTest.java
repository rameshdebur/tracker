package org.opensourcephysics.cabrillo.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import java.lang.reflect.Field;
import java.util.Map;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import sun.reflect.ReflectionFactory;
import java.lang.reflect.Constructor;

public class UndoTest {

    private TrackerPanel dummyPanel;

    @Before
    public void setUp() throws Exception {
        // Instantiate without calling the constructor using ReflectionFactory
        ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
        Constructor<?> objDef = Object.class.getDeclaredConstructor();
        Constructor<?> intConstr = rf.newConstructorForSerialization(TrackerPanel.class, objDef);
        dummyPanel = (TrackerPanel) intConstr.newInstance();

        // set 'panelID' using reflection
        Field idField = TrackerPanel.class.getDeclaredField("panelID");
        idField.setAccessible(true);
        idField.set(dummyPanel, 999);
    }

    @After
    public void tearDown() throws Exception {
        // Clear the static undo map
        Field mapField = Undo.class.getDeclaredField("undomap");
        mapField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Integer, Undo> map = (Map<Integer, Undo>) mapField.get(null);
        map.clear();
    }

    @Test
    public void testCanRedo() throws Exception {
        // canRedo should be false initially
        assertFalse("canRedo should be false initially", Undo.canRedo(dummyPanel));

        // Get the Undo object that was created
        Field mapField = Undo.class.getDeclaredField("undomap");
        mapField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Integer, Undo> map = (Map<Integer, Undo>) mapField.get(null);
        Undo undo = map.get(999);
        assertNotNull("Undo object should be created", undo);

        // Access undoManager
        Field managerField = Undo.class.getDeclaredField("undoManager");
        managerField.setAccessible(true);
        Undo.MyUndoManager undoManager = (Undo.MyUndoManager) managerField.get(undo);

        // Create a dummy UndoableEdit
        UndoableEdit edit = new UndoableEdit() {
            @Override
            public void undo() throws CannotUndoException {}
            @Override
            public boolean canUndo() { return true; }
            @Override
            public void redo() throws CannotRedoException {}
            @Override
            public boolean canRedo() { return true; }
            @Override
            public void die() {}
            @Override
            public boolean addEdit(UndoableEdit anEdit) { return false; }
            @Override
            public boolean replaceEdit(UndoableEdit anEdit) { return false; }
            @Override
            public boolean isSignificant() { return true; }
            @Override
            public String getPresentationName() { return "Test"; }
            @Override
            public String getUndoPresentationName() { return "Undo Test"; }
            @Override
            public String getRedoPresentationName() { return "Redo Test"; }
        };

        // Add edit to manager
        undoManager.addEdit(edit);

        // At this point we can undo it, but we can't redo it yet
        assertFalse("canRedo should be false before undo", Undo.canRedo(dummyPanel));

        // Now undo the edit
        undoManager.undo();

        // After undoing, we should be able to redo
        assertTrue("canRedo should be true after undoing an edit", Undo.canRedo(dummyPanel));

        // Redo the edit
        undoManager.redo();

        // We should no longer be able to redo
        assertFalse("canRedo should be false after redoing", Undo.canRedo(dummyPanel));
    }
}
