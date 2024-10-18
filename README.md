# Personal Project
## A Book Cataloguing System
For bookworms and casual readers alike, the common issue is remembering books that you have read, and remembering what you thought of them.
While there are other ways of combating this, sometimes the best route is a more personal one. 
This application is a book cataloguing system, which acts as a library for one. 


Although I do most of my reading digitally nowadays, I still have far too many physical books. A sizeable portion of which, I *still* have not read.
When planning on getting rid of books, or buying new ones, I'd like to know what I already own without having to scan my shelves and stacked piles.
(There's been a few times when I've bought the same book twice, so it would be best if that was avoided in the future.) 

Through this personal library, a user will be able to keep track of every book they own. Cataloguing if they've read it, what they thought of it, the book's condition, and if they let a friend borrow it.

Say goodbye to forgetfulness (for this one specific case, anyways.)

## User Stories
In the context of a personal library:
- As a user, I want to be able to add **multiple** books to my collection
- As a user, I want to be able to **search** by a specific title or author
- As a user, I want to be able to **remove** a book from my collection
- As a user, I want to be able to **rate** a book in my collection
- As a user, I want to be able to **write a review** for a book I've read
- As a user, I want to be able to **view** certain lists of books based on my given parameters
- As a user, I want to be able to **save** my collection to file
- As a user, I want to be able to **load** my collection from file

## Phase 4: Task 2
I have decided to make the Collection class in my model package robust. 
- model.Collection.getBookAtIndex now throws IndexGreaterThanException if the index parameter exceeds the collection size.
- model.Collection.removeBook now throws BookDoesNotExistException if the book parameter is not in the collection. 


- The BookDoesNotExistException is caught by ui.gui.EditCard.makePopUp, which displays a second pop-up when a book cannot be removed.
- The Library class catches IndexGreaterThanException in ui.Library.getCertainBook, which then prints out an error message to console.
- The Library class also catches BookDoesNotExistException in ui.Library.removeChosenBook, which then prints an error message to console.


## Phase 4: Task 3
Given more time to work on this project:
- Ideally, UpdateCollection would just call Collection, which could then access Book. Refactoring with this in mind would remove UpdateCollection's association with Book.
- I would refactor MakeNewBook to remove the association with the Collection class. MakeNewBook could simply have a method which returns the book created, which could then be added to the collection when called in Library.
- For both EditCard and ViewCard, I would remove the associations to the other cards (AddBookCard and SearchCard for EditCard, the former for ViewCard). I could add a new class containing the specific panels meant for inputting information, then perhaps create all cards in one class that extends UpdateCollection and includes a reference to this other new class meant for user input. 
- I would refactor SearchCard to remove the association with the Book class, since SearchCard extends Update Collection, which includes a call to Collection, I should be able to just find a book based on these associations.