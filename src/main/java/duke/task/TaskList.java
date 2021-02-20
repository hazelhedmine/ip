package duke.task;

import duke.exception.DescriptionFieldEmptyException;
import duke.exception.MultipleTimeFieldsException;
import duke.exception.TimeFieldEmptyException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;
    private final int COMMAND_TASK_SEPARATOR = 2;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTaskList() {
        return tasks;
    }

    public void printListMessage() {
        System.out.println(" Here are the tasks in your list:");
        for (Task task: tasks) {
            System.out.println(" " + (tasks.indexOf(task) + 1) + "." + task.toString());
        }
    }

    public void markTaskAsDone(String[] command) {
        Task task;
        try {
            int doneTaskNumber = Integer.parseInt(command[1]);
            task = tasks.get(doneTaskNumber - 1);
            task.markAsDone();
            printDoneMessage(task);
        } catch (IndexOutOfBoundsException e) {
            printNonExistentTaskMessage();
        } catch (NumberFormatException e) {
            printNotANumberMessage();
        }
    }

    public static void printDoneMessage(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task.toString());
    }

    public void addDeadline(String[] command) {
        String[] description;
        try {
            description = command[1].split("/by", COMMAND_TASK_SEPARATOR);
            checkForValidDeadlineInput(description);
            Deadline deadline = new Deadline(description[0].trim(), description[1].trim());
            tasks.add(deadline);
            printAddedMessage(deadline);
        } catch (ArrayIndexOutOfBoundsException e) {
            printMissingFieldsMessage();
        } catch (DescriptionFieldEmptyException e) {
            printDescriptionFieldEmptyMessage();
        } catch (TimeFieldEmptyException e) {
            printTimeFieldEmptyMessage();
        } catch (MultipleTimeFieldsException e) {
            printTooManyTimeFieldsMessage();
        }
    }

    public void addEvent(String[] command) {
        String[] description;
        try {
            description = command[1].split("/at", COMMAND_TASK_SEPARATOR);
            checkForValidEventInput(description);
            Event event = new Event(description[0].trim(), description[1].trim());
            tasks.add(event);
            printAddedMessage(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            printMissingFieldsMessage();
        } catch (DescriptionFieldEmptyException e) {
            printDescriptionFieldEmptyMessage();
        } catch (TimeFieldEmptyException e) {
            printTimeFieldEmptyMessage();
        } catch (MultipleTimeFieldsException e) {
            printTooManyTimeFieldsMessage();
        }
    }

    public void addToDo(String[] command) {
        try {
            ToDo toDo = new ToDo(command[1]);
            tasks.add(toDo);
            printAddedMessage(toDo);
        } catch (ArrayIndexOutOfBoundsException e) {
            printMissingFieldsMessage();
        }
    }

    public  void printAddedMessage(Task task) {
        System.out.println(" Alright, I've added this task:\n   " + task.toString() + "\n"
                + " Now you have " + tasks.size() + " tasks in your list.");
    }

    public  void deleteTask(String[] command) {
        try {
            int taskNumberToBeDeleted = Integer.parseInt(command[1]);
            Task deletedTask = tasks.get(taskNumberToBeDeleted - 1);
            tasks.remove(deletedTask);
            printDeletedMessage(deletedTask);
        } catch (IndexOutOfBoundsException e) {
            printNonExistentTaskMessage();
        } catch (NumberFormatException e) {
            printNotANumberMessage();
        }
    }

    public void printDeletedMessage(Task task) {
        System.out.println(" Alright, I've deleted this task:\n   " + task.toString() + "\n"
                + " Now you have " + tasks.size() + " tasks in your list.");
    }

    public void checkForValidDeadlineInput(String[] input) throws DescriptionFieldEmptyException,
            TimeFieldEmptyException,
            MultipleTimeFieldsException {
        if (input[0].trim().equals("")) {
            throw new DescriptionFieldEmptyException();
        } else if (input[1].contains("/by")) {
            throw new MultipleTimeFieldsException();
        } else if (input[1].trim().equals("")) {
            throw new TimeFieldEmptyException();
        }
    }

    public void checkForValidEventInput(String[] input) throws DescriptionFieldEmptyException,
            TimeFieldEmptyException,
            MultipleTimeFieldsException {
        if (input[0].trim().equals("")) {
            throw new DescriptionFieldEmptyException();
        } else if (input[1].contains("/at")) {
            throw new MultipleTimeFieldsException();
        } else if (input[1].trim().equals("")) {
            throw new TimeFieldEmptyException();
        }
    }

    public void printDescriptionFieldEmptyMessage() {
        System.out.println(" ERROR: the description field of a task cannot be empty :(");
    }

    public void printTimeFieldEmptyMessage() {
        System.out.println(" ERROR: the time field of a task cannot be empty :(");
    }

    public void printMissingFieldsMessage() {
        System.out.println(" ERROR: make sure that you've input a description and time field!");
    }

    public void printTooManyTimeFieldsMessage() {
        System.out.println(" ERROR: too many timings, try again with just one!");
    }

    public void printNonExistentTaskMessage() {
        System.out.println(" ERROR: this task number doesn't exist!");
    }

    public void printNotANumberMessage() {
        System.out.println(" ERROR: this is not a task number!");
    }
}
