package seedu.address.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.deliverable.deliverable.Deliverable;

/**
 * Panel containing the expanded details of a deliverable.
 */
public class ProjectCompletionStatusPanel extends UiPart<Region> {
    private static final String FXML = "ProjectCompletionStatusPanel.fxml";
    private ObservableList<Deliverable> deliverableList;
    private int totalNumDeliverables;
    private int numCompletedDeliverables;
    private int overallCompletionPercentage;

    @FXML
    private StackPane ocpDiagram;

    @FXML
    private Label ocpCaption;

    /**
     * Creates a {@code ProjectCompletionStatusPanel} with the given {@code ObservableList<Deliverable>}
     */
    public ProjectCompletionStatusPanel(ObservableList<Deliverable> deliverableList) {
        super(FXML);
        this.deliverableList = deliverableList;
        updateOcp();
    }

    private int findNumCompletedDeliverables(ObservableList<Deliverable> deliverableList) {
        int numCompletedDeliverables = 0;
        for (Deliverable deliverable : deliverableList) {
            if (deliverable.getIsComplete()) {
                numCompletedDeliverables++;
            }
        }
        return numCompletedDeliverables;
    }

    private int getOcp(int totalNumDeliverables, int numCompletedDeliverables) {
        return totalNumDeliverables == 0
                ? 0
                : (int) ((double) numCompletedDeliverables / (double) totalNumDeliverables * 100);
    }

    /**
     * Updates OCP for every Deliverable command execution.
     */
    public void updateOcp() {
        totalNumDeliverables = deliverableList.size();
        numCompletedDeliverables = findNumCompletedDeliverables(deliverableList);
        overallCompletionPercentage = getOcp(totalNumDeliverables, numCompletedDeliverables);

        // @@author gabztcr-reused
        // Reused from https://www.youtube.com/watch?v=9SEE8UP17jo with minor modifications.
        RingProgressIndicator ringProgressIndicator = new RingProgressIndicator();
        ocpDiagram.getChildren().add(ringProgressIndicator);
        new WorkerThread(ringProgressIndicator, overallCompletionPercentage).start();
        // @@author

        String caption = numCompletedDeliverables + " / " + totalNumDeliverables + " Deliverables Completed";
        ocpCaption.setText(caption);
    }

    // @@author gabztcr-reused
    // Reused from https://www.youtube.com/watch?v=9SEE8UP17jo with minor modifications.
    public class WorkerThread extends Thread {
        private RingProgressIndicator rpi;
        private int progress = 0;
        private int ocp;

        /**
         * Constructor class for the worker thread.
         * @param rpi RingProgressIndicator object
         * @param ocp Overall Completion Percentage for Product Development
         */
        public WorkerThread(RingProgressIndicator rpi, int ocp) {
            this.rpi = rpi;
            this.ocp = ocp;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    Logger.getLogger(ProjectCompletionStatusPanel.class.getName()).log(Level.SEVERE, null, e);
                }

                if (progress >= ocp) {
                    break;
                }

                Platform.runLater(() -> {
                    rpi.setProgress(progress);
                });

                progress++;
            }
        }
    }
    // @@author
}
