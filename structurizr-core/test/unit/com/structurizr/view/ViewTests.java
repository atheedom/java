package com.structurizr.view;

import com.structurizr.AbstractWorkspaceTestBase;
import com.structurizr.model.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ViewTests extends AbstractWorkspaceTestBase {

    @Test
    public void test_description() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        View view = new SystemContextView(softwareSystem);
        view.setDescription("Some description");
        assertEquals("Some description", view.getDescription());
    }

    @Test
    public void test_addAllSoftwareSystems_DoesNothing_WhenThereAreNoOtherSoftwareSystemsInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        View view = new SystemContextView(softwareSystem);
        assertEquals(1, view.getElements().size());
        view.addAllSoftwareSystems();
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addAllSoftwareSystems_DoesAddAllSoftwareSystems_WhenThereAreSoftwareSystemsInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem(Location.Unspecified, "System A", "Description");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem(Location.Unspecified, "System B", "Description");
        SoftwareSystem softwareSystemC = model.addSoftwareSystem(Location.Unspecified, "System C", "Description");

        View view = new SystemContextView(softwareSystem);
        view.addAllSoftwareSystems();

        assertEquals(4, view.getElements().size());
        Iterator<ElementView> it = view.getElements().iterator();
        assertSame(softwareSystem, it.next().getElement());
        assertSame(softwareSystemA, it.next().getElement());
        assertSame(softwareSystemB, it.next().getElement());
        assertSame(softwareSystemC, it.next().getElement());
    }

    @Test
    public void test_addSoftwareSystem_DoesNothing_WhenGivenNull() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        View view = new SystemContextView(softwareSystem);
        view.addSoftwareSystem(null);
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addSoftwareSystem_DoesNothing_WhenTheSoftwareSystemIsNotInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        View view = new SystemContextView(softwareSystem);

        Model model2 = new Model();
        SoftwareSystem softwareSystemA = model2.addSoftwareSystem(Location.Unspecified, "System A", "Description");
        view.addSoftwareSystem(softwareSystemA);
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addSoftwareSystem_AddsTheSoftwareSystem_WhenTheSoftwareSystemIsInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem(Location.Unspecified, "System A", "Description");

        View view = new SystemContextView(softwareSystem);
        view.addSoftwareSystem(softwareSystemA);
        assertEquals(2, view.getElements().size());
        Iterator<ElementView> it = view.getElements().iterator();
        assertSame(softwareSystem, it.next().getElement());
        assertSame(softwareSystemA, it.next().getElement());
    }

    @Test
    public void test_addAllPeople_DoesNothing_WhenThereAreNoPeopleInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");

        View view = new SystemContextView(softwareSystem);
        assertEquals(1, view.getElements().size());

        view.addAllPeople();
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addAllPeople_DoesAddAllPeople_WhenThereArePeopleInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        Person person1 = model.addPerson(Location.Unspecified, "Person 1", "Description");
        Person person2 = model.addPerson(Location.Unspecified, "Person 2", "Description");
        Person person3 = model.addPerson(Location.Unspecified, "Person 3", "Description");

        View view = new SystemContextView(softwareSystem);
        view.addAllPeople();

        assertEquals(4, view.getElements().size());
        Iterator<ElementView> it = view.getElements().iterator();
        assertSame(softwareSystem, it.next().getElement());
        assertSame(person1, it.next().getElement());
        assertSame(person2, it.next().getElement());
        assertSame(person3, it.next().getElement());
    }

    @Test
    public void test_addPerson_DoesNothing_WhenGivenNull() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        View view = new SystemContextView(softwareSystem);
        view.addPerson(null);
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addPerson_DoesNothing_WhenThePersonIsNotInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        View view = new SystemContextView(softwareSystem);

        Model model2 = new Model();
        Person person1 = model2.addPerson(Location.Unspecified, "Person 1", "Description");
        view.addPerson(person1);
        assertEquals(1, view.getElements().size());
    }

    @Test
    public void test_addPerson_AddsThePerson_WhenThPersonIsInTheModel() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        View view = new SystemContextView(softwareSystem);

        Person person1 = model.addPerson(Location.Unspecified, "Person 1", "Description");
        view.addPerson(person1);

        assertEquals(2, view.getElements().size());
        Iterator<ElementView> it = view.getElements().iterator();
        assertSame(softwareSystem, it.next().getElement());
        assertSame(person1, it.next().getElement());
    }

    @Test
    public void test_removeElementsWithNoRelationships_RemovesAllElements_WhenTheViewHasNoRelationshipsBetweenElements() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem(Location.Unspecified, "System A", "Description");
        Person person1 = model.addPerson(Location.Unspecified, "Person 1", "Description");

        View view = new SystemContextView(softwareSystem);
        view.addAllSoftwareSystems();
        view.addAllPeople();
        view.removeElementsWithNoRelationships();

        assertEquals(0, view.getElements().size());
    }

    @Test
    public void test_removeElementsWithNoRelationships_RemovesOnlyThoseElementsWithoutRelationships_WhenTheViewContainsSomeUnlinkedElements() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem(Location.Unspecified, "System A", "Description");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem(Location.Unspecified, "System B", "Description");
        Person person1 = model.addPerson(Location.Unspecified, "Person 1", "Description");
        Person person2 = model.addPerson(Location.Unspecified, "Person 2", "Description");
        View view = new SystemContextView(softwareSystem);

        softwareSystem.uses(softwareSystemA, "uses");
        person1.uses(softwareSystem, "uses");

        view.addAllSoftwareSystems();
        view.addAllPeople();
        assertEquals(5, view.getElements().size());

        view.removeElementsWithNoRelationships();
        assertEquals(3, view.getElements().size());
    }

    @Test
    public void test_copyLayoutInformationFrom() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem(Location.Unspecified, "System A", "Description");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem(Location.Unspecified, "System B", "Description");
        Person person = model.addPerson(Location.Unspecified, "Person", "Description");

        Relationship personUsesSoftwareSystem = person.uses(softwareSystem, "Uses");

        // create a view with SystemA and Person (locations are set for both, relationship has vertices)
        View view1 = new SystemContextView(softwareSystem);
        view1.addSoftwareSystem(softwareSystemA);
        view1.findElementView(softwareSystemA).setX(123);
        view1.findElementView(softwareSystemA).setY(321);
        view1.addPerson(person);
        view1.findElementView(person).setX(456);
        view1.findElementView(person).setY(654);
        view1.findRelationshipView(personUsesSoftwareSystem).setVertices(Arrays.asList(new Vertex(123, 456)));

        // create a view with SystemB and Person (locations are 0,0 for both)
        View view2 = new SystemContextView(softwareSystem);
        view2.addSoftwareSystem(softwareSystemB);
        view2.addPerson(person);
        assertEquals(0, view2.findElementView(softwareSystemB).getX());
        assertEquals(0, view2.findElementView(softwareSystemB).getY());
        assertEquals(0, view2.findElementView(person).getX());
        assertEquals(0, view2.findElementView(person).getY());
        assertTrue(view2.findRelationshipView(personUsesSoftwareSystem).getVertices().isEmpty());

        view2.copyLayoutInformationFrom(view1);
        assertEquals(0, view2.findElementView(softwareSystemB).getX());
        assertEquals(0, view2.findElementView(softwareSystemB).getY());
        assertEquals(456, view2.findElementView(person).getX());
        assertEquals(654, view2.findElementView(person).getY());
        Vertex vertex = view2.findRelationshipView(personUsesSoftwareSystem).getVertices().iterator().next();
        assertEquals(123, vertex.getX());
        assertEquals(456, vertex.getY());
    }

    @Test
    public void test_getTitle() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        SystemContextView systemContextView = new SystemContextView(softwareSystem);
        assertEquals("The System - System Context", systemContextView.getTitle());

        systemContextView.setDescription(null);
        assertEquals("The System - System Context", systemContextView.getTitle());

        systemContextView.setDescription("");
        assertEquals("The System - System Context", systemContextView.getTitle());

        systemContextView.setDescription("   ");
        assertEquals("The System - System Context", systemContextView.getTitle());

        systemContextView.setDescription("Some description");
        assertEquals("The System - System Context [Some description]", systemContextView.getTitle());
    }

    @Test
    public void test_removeElementsThatCantBeReachedFrom_DoesNothing_WhenANullElementIsSpecified() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "The System", "Description");
        View view = new SystemContextView(softwareSystem);
        view.removeElementsThatCantBeReachedFrom(null);
    }

    @Test
    public void test_removeElementsThatCantBeReachedFrom_DoesNothing_WhenAllElementsCanBeReached() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem("The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem("System A", "");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem("System B", "");

        softwareSystem.uses(softwareSystemA, "uses");
        softwareSystemA.uses(softwareSystemB, "uses");

        View view = new SystemContextView(softwareSystem);
        view.addAllElements();
        assertEquals(3, view.getElements().size());

        view.removeElementsThatCantBeReachedFrom(softwareSystem);
        assertEquals(3, view.getElements().size());
    }

    @Test
    public void test_removeElementsThatCantBeReachedFrom_RemovesOrphanedElements_WhenThereAreSomeOrphanedElements() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem("The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem("System A", "");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem("System B", "");
        SoftwareSystem softwareSystemC = model.addSoftwareSystem("System C", "");

        softwareSystem.uses(softwareSystemA, "uses");
        softwareSystemA.uses(softwareSystemB, "uses");

        View view = new SystemContextView(softwareSystem);
        view.addAllElements();
        assertEquals(4, view.getElements().size());

        view.removeElementsThatCantBeReachedFrom(softwareSystem);
        assertEquals(3, view.getElements().size());
        assertFalse(view.getElements().contains(new ElementView(softwareSystemC)));
    }

    @Test
    public void test_removeElementsThatCantBeReachedFrom_RemovesUnreachableElements_WhenThereAreSomeUnreachableElements() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem("The System", "Description");
        SoftwareSystem softwareSystemA = model.addSoftwareSystem("System A", "");
        SoftwareSystem softwareSystemB = model.addSoftwareSystem("System B", "");

        softwareSystem.uses(softwareSystemA, "uses");
        softwareSystemA.uses(softwareSystemB, "uses");

        View view = new SystemContextView(softwareSystem);
        view.addAllElements();
        assertEquals(3, view.getElements().size());

        view.removeElementsThatCantBeReachedFrom(softwareSystemA);
        assertEquals(2, view.getElements().size());
        assertFalse(view.getElements().contains(new ElementView(softwareSystem)));
    }

}
