package org.opensourcephysics.cabrillo.tracker;

import org.junit.Test;
import static org.junit.Assert.*;

public class TViewChooserTest {

    @Test
    public void testIsSelectedView_NullView() {
        // Since view is null, getChooserParent tries to call getParent() on null which causes NPE.
        // The previous test logic catching NPE was correct! Let's put it back.
        try {
            TViewChooser.isSelectedView(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testIsSelectedView_NoChooserParent() {
        TView dummyView = new DummyTView();
        assertFalse(TViewChooser.isSelectedView(dummyView));
    }

    @Test
    public void testIsSelectedView_WithChooser_Selected() {
        DummyTViewChooser chooser = new DummyTViewChooser();
        TView dummyView = new DummyTView();
        chooser.add(dummyView); // add it to the chooser to set parent
        chooser.setSelectedView(dummyView);

        assertTrue(TViewChooser.isSelectedView(dummyView));
    }

    @Test
    public void testIsSelectedView_WithChooser_NotSelected() {
        DummyTViewChooser chooser = new DummyTViewChooser();
        TView dummyView1 = new DummyTView();
        TView dummyView2 = new DummyTView();
        chooser.add(dummyView1);
        chooser.add(dummyView2);

        chooser.setSelectedView(dummyView2);

        assertFalse(TViewChooser.isSelectedView(dummyView1));
    }

    // Creating mock classes bypassing the TView and TViewChooser constructor logic
    private static class DummyTView extends TView {
        // We override the panel variable in the constructor to avoid NPEs when accessing frame
        public DummyTView() {
            super(new DummyTrackerPanel());
        }
        @Override public void refresh() {}
        @Override public int getViewType() { return 0; }
        @Override public String getName() { return "Dummy"; }
        @Override public String getViewName() { return "Dummy"; }
        @Override public javax.swing.Icon getViewIcon() { return null; }
        @Override public TrackerPanel getTrackerPanel() { return null; }
        @Override public void cleanup() {}
        @Override public void init() {}
        @Override public void propertyChange(java.beans.PropertyChangeEvent evt) {}
        @Override public void dispose() {}
    }

    private static class DummyTrackerPanel extends TrackerPanel {
        public DummyTrackerPanel() {
            super(true); // boolean ignored constructor avoids TFrame logic
        }
        @Override public TFrame getTFrame() { return new DummyTFrame(); } // bypass frame check
        @Override public Integer getID() { return 0; }
    }

    private static class DummyTFrame extends TFrame {
        public DummyTFrame() {
            super();
        }
        @Override public TrackerPanel getTrackerPanelForID(Integer id) {
            return new DummyTrackerPanel(); // prevent NPE in TViewChooser constructor
        }
    }

    private static class DummyTViewChooser extends TViewChooser {
        private TView selectedView;

        public DummyTViewChooser() {
            super(new DummyTrackerPanel(), 0);
        }

        public void setSelectedView(TView view) {
            this.selectedView = view;
        }

        @Override
        public TView getSelectedView() {
            return this.selectedView;
        }
    }
}
