package seedu.address.model.assignment;

import seedu.address.commons.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ModuleCodeContainsKeywordsPredicate implements Predicate<Assignment> {
    private final List<String> keywords;

    public ModuleCodeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Assignment assignment) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil
                        .containsWordIgnoreCase(assignment.getModuleCode().moduleCode, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCodeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ModuleCodeContainsKeywordsPredicate) other).keywords)); // state check
    }

}