package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ASSIGNMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.testutil.AssignmentBuilder;

public class UnremindCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnremindCommand(null));
    }

    @Test
    public void execute_validIndex_success() {
        remindFirstAssignment();
        Assignment assignmentToUnremind = model.getRemindedAssignmentsList().get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        UnremindCommand unremindCommand = new UnremindCommand(INDEX_FIRST_ASSIGNMENT);

        String expectedMessage = String.format(
                UnremindCommand.MESSAGE_UNREMIND_ASSIGNMENT_SUCCESS, assignmentToUnremind);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                model.getPreviousModel());
        expectedModel.setAssignment(model.getRemindedAssignmentsList().get(0), assignmentToUnremind);

        assertCommandSuccess(unremindCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getRemindedAssignmentsList().size() + 1);
        UnremindCommand unremindCommand = new UnremindCommand(outOfBoundIndex);

        assertCommandFailure(unremindCommand, model, Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        remindFirstAssignment();
        remindSecondAssignment();

        UnremindCommand unremindFirstCommand = new UnremindCommand(INDEX_FIRST_ASSIGNMENT);
        UnremindCommand unremindSecondCommand = new UnremindCommand(INDEX_SECOND_ASSIGNMENT);

        // same object -> returns true
        assertTrue(unremindFirstCommand.equals(unremindFirstCommand));

        // same values -> returns true
        UnremindCommand unremindFirstCommandCopy = new UnremindCommand(INDEX_FIRST_ASSIGNMENT);
        assertTrue(unremindFirstCommand.equals(unremindFirstCommandCopy));

        // different types -> returns false
        assertFalse(unremindFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unremindFirstCommand.equals(null));

        // different assignment -> returns false
        assertFalse(unremindFirstCommand.equals(unremindSecondCommand));
    }

    // Reminded first assignment in filtered list
    private void remindFirstAssignment() {
        // Set reminders for assignment in filtered list in address book
        Assignment assignmentInList = model.getAddressBook().getAssignmentList()
                .get(INDEX_FIRST_ASSIGNMENT.getZeroBased());
        Assignment assignmentInListReminded = new AssignmentBuilder(assignmentInList).withRemindersSet().build();
        model.setAssignment(assignmentInList, assignmentInListReminded);
    }

    // Reminded second assignment in filtered list
    private void remindSecondAssignment() {
        // Set reminders for assignment in filtered list in address book
        Assignment assignmentInList = model.getAddressBook().getAssignmentList()
                .get(INDEX_SECOND_ASSIGNMENT.getZeroBased());
        Assignment assignmentInListReminded = new AssignmentBuilder(assignmentInList).withRemindersSet().build();
        model.setAssignment(assignmentInList, assignmentInListReminded);
    }
}
