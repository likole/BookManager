package cn.likole.bookmanager.bean;

/**
 * Created by _SOLID
 * Date:2016/3/30
 * Time:14:18
 * <p/>
 * 书籍实体类
 */

//[{"bookId":1000,"bookIsbn":"BX8888","bookNumber":99,"bookBorrow":1,"bookImage":"SADLHJKALSHFJKLASHNFLASKFNAKSL","bookTitle":"张泰龙大帅比","bookIntro":"张泰龙是个很帅很帅的bb","bookAuthor":"张泰龙","authorIntro":null}
public class BookBean {
    private int bookId;
    private String bookIsbn;
    private int bookNumber;
    private int bookBorrow;
    private String bookImage;
    private String bookTitle;
    private String bookIntro;
    private String bookAuthor;
    private String authorIntro;

    @Override
    public String toString() {
        return "BookBean{" +
                "bookId=" + bookId +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", bookNumber=" + bookNumber +
                ", bookBorrow=" + bookBorrow +
                ", bookImage='" + bookImage + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookIntro='" + bookIntro + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", authorIntro='" + authorIntro + '\'' +
                '}';
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public int getBookBorrow() {
        return bookBorrow;
    }

    public void setBookBorrow(int bookBorrow) {
        this.bookBorrow = bookBorrow;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public void setAuthorIntro(String authorIntro) {
        this.authorIntro = authorIntro;
    }
}
