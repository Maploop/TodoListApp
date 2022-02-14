package net.jet3.booking101.util;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IDraggable {
    public enum Event {
        EMPTY, START_DRAG, WHILE_DRAG, END_DRAG
    }

    public interface IInterface {
        IDraggable.Feature getDraggableFeature();
    }

    public interface Listener {
        void accept(Feature f, Event e);
    }

    public static class Feature implements EventHandler<MouseEvent> {
        private double lastMouseX = 0, lastMouseY = 0;

        private boolean dragging = false;

        private final boolean enabled = true;
        private final Node eventNode;
        private final List<Node> dragNodes = new ArrayList<>();
        private final List<Listener> dragListeners = new ArrayList<>();

        public Feature(final Node node) {
            this(node, node);
        }

        public Feature(final Node e, final Node... dragNodes) {
            this.eventNode = e;
            this.dragNodes.addAll(Arrays.asList(dragNodes));
            this.eventNode.addEventHandler(MouseEvent.ANY, this);
        }

        public final boolean push(final Node node) {
            if (!this.dragNodes.contains(node)) {
                return this.dragNodes.add(node);
            }
            return false;
        }

        public final boolean addListener(final Listener listener) {
            return this.dragListeners.add(listener);
        }

        public final void remove() {
            this.eventNode.removeEventFilter(MouseEvent.ANY, this);
        }

        public final List<Node> getNodes() {
            return new ArrayList<>(this.dragNodes);
        }

        public final Node getEventNode() {
            return this.eventNode;
        }

        @Override
        public void handle(MouseEvent event) {
            if (MouseEvent.MOUSE_PRESSED == event.getEventType()) {
                if (this.enabled && this.eventNode.contains(event.getX(), event.getY())) {
                    this.lastMouseX = event.getSceneX();
                    this.lastMouseY = event.getSceneY();
                    event.consume();
                }
            } else if (MouseEvent.MOUSE_DRAGGED == event.getEventType()) {
                if (!this.dragging) {
                    this.dragging = true;
                    for (final Listener listener : this.dragListeners) {
                        listener.accept(this, Event.START_DRAG);
                    }
                }
                if (this.dragging) {
                    final double deltaX = event.getSceneX() - this.lastMouseX;
                    final double deltaY = event.getSceneY() - this.lastMouseY;

                    for (final Node dragNode : this.dragNodes) {
                        final double initialTranslateX = dragNode.getTranslateX();
                        final double initialTranslateY = dragNode.getTranslateY();
                        dragNode.setTranslateX(initialTranslateX + deltaX);
                        dragNode.setTranslateY(initialTranslateY + deltaY);
                    }

                    this.lastMouseX = event.getSceneX();
                    this.lastMouseY = event.getSceneY();

                    event.consume();
                    for (final Listener listener : this.dragListeners) {
                        listener.accept(this, Event.WHILE_DRAG);
                    }
                }
            } else if (MouseEvent.MOUSE_RELEASED == event.getEventType()) {
                if (this.dragging) {
                    event.consume();
                    this.dragging = false;
                    for (final Listener listener : this.dragListeners) {
                        listener.accept(this, Event.END_DRAG);
                    }
                }
            }
        }

        public final boolean removeNode(final Node node) {
            return this.dragNodes.remove(node);
        }

        public final boolean removeListener(final Listener listener) {
            return this.dragListeners.remove(listener);
        }

        public final void setLastMouse(final double lastMouseX, final double lastMouseY) {
            this.lastMouseX = lastMouseX;
            this.lastMouseY = lastMouseY;
        }
    }
}
