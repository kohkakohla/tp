package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CLARA;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Subject;

public class UniqueLessonListTest {
    @Test
    public void contains_nullLesson_throwsNullPointerException() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        assertThrows(NullPointerException.class, () -> uniqueLessonList.contains(null));
    }

    @Test
    public void contains_lessonNotInList_returnsFalse() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        assertFalse(uniqueLessonList.contains(new Lesson(ALICE, DANIEL, new Subject("Math"))));
    }

    @Test
    public void contains_lessonInList_returnsTrue() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        uniqueLessonList.add(new Lesson(ALICE, DANIEL, new Subject("Math")));
        assertTrue(uniqueLessonList.contains(new Lesson(ALICE, DANIEL, new Subject("Math"))));
    }

    @Test
    public void add_nullLesson_throwsNullPointerException() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        assertThrows(NullPointerException.class, () -> uniqueLessonList.add(null));
    }

    @Test
    public void add_duplicateLesson_throwsDuplicateLessonException() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        Lesson lesson = new Lesson(ALICE, DANIEL, new Subject("Math"));
        uniqueLessonList.add(lesson);
        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.add(lesson));
    }

    @Test
    public void setLessons_nullUniqueLessonList_throwsNullPointerException() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLessons(null));
    }

    @Test
    public void setLessons_uniqueLessonList_replacesOwnListWithProvidedUniqueLessonList() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        uniqueLessonList.add(new Lesson(ALICE, DANIEL, new Subject("Math")));
        List<Lesson> replacement = new ArrayList<>();
        replacement.add(new Lesson(BENSON, CLARA, new Subject("English")));
        uniqueLessonList.setLessons(replacement);
    }

    @Test
    public void getAssociatedPeople_null_throwsNullPointerException() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        assertThrows(NullPointerException.class, () -> uniqueLessonList.getAssociatedPeople(null));
    }

    @Test
    public void getAssociatedPeople_singleLesson_returnsAssociatedPeople() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        Subject subject = new Subject("Math");
        uniqueLessonList.add(new Lesson(ALICE, DANIEL, subject));
        ArrayList<Map.Entry<Person, Subject>> expected = new ArrayList<>();
        expected.add(Map.entry(DANIEL, subject));
        assertEquals(uniqueLessonList.getAssociatedPeople(ALICE), expected);
    }

    @Test
    public void getAssociatedPeople_multipleLessons_returnsAssociatedPeople() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        Subject math = new Subject("Math");
        Subject english = new Subject("English");
        uniqueLessonList.add(new Lesson(ALICE, DANIEL, math));
        uniqueLessonList.add(new Lesson(ALICE, CLARA, english));
        ArrayList<Map.Entry<Person, Subject>> expected = new ArrayList<>();
        expected.add(Map.entry(DANIEL, math));
        expected.add(Map.entry(CLARA, english));
        assertEquals(uniqueLessonList.getAssociatedPeople(ALICE), expected);
    }

    @Test
    public void remove_nullLesson_throwsNullPointerException() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        assertThrows(NullPointerException.class, () -> uniqueLessonList.remove(null));
    }

    @Test
    public void remove_lessonNotInList_throwsLessonNotFoundException() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        Lesson lesson = new Lesson(ALICE, DANIEL, new Subject("Math"));
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.remove(lesson));
    }

    @Test
    public void remove_existingLesson_removesLesson() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        Lesson lesson = new Lesson(ALICE, DANIEL, new Subject("Math"));
        uniqueLessonList.add(lesson);
        uniqueLessonList.remove(lesson);
        UniqueLessonList expectedUniqueLessonList = new UniqueLessonList();
        assertEquals(expectedUniqueLessonList, uniqueLessonList);
    }

    @Test
    public void equals() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        uniqueLessonList.add(new Lesson(ALICE, DANIEL, new Subject("Math")));
        UniqueLessonList copy = new UniqueLessonList();
        copy.add(new Lesson(ALICE, DANIEL, new Subject("Math")));
        assertTrue(uniqueLessonList.equals(copy));

        UniqueLessonList different = new UniqueLessonList();
        different.add(new Lesson(ALICE, DANIEL, new Subject("Math")));
        different.add(new Lesson(ALICE, CLARA, new Subject("English")));
        assertFalse(uniqueLessonList.equals(different));

        assertFalse(uniqueLessonList.equals(null));

        assertFalse(uniqueLessonList.equals(new UniqueLessonList()));
    }
}
