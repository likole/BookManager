package cn.likole.bookmanager.bean;

/**
 * Created by likole on 3/30/18.
 */


public class BorrowBean {
    private BorrowInfo borrowInfo;
    private UserBean userInfo;
    private BookBean book;

    @Override
    public String toString() {
        return "BorrowBean{" +
                "borrowInfo=" + borrowInfo +
                ", userInfo=" + userInfo +
                ", book=" + book +
                '}';
    }

    public BorrowInfo getBorrowInfo() {
        return borrowInfo;
    }

    public void setBorrowInfo(BorrowInfo borrowInfo) {
        this.borrowInfo = borrowInfo;
    }

    public UserBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserBean userInfo) {
        this.userInfo = userInfo;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public class BorrowInfo {
          /*
    "borrowId": 1002,
        "borrowUserId": 1000,
        "borrowTime": 1521549344000,
        "returnTime": null,
        "borrowState": 0,
        "bookId": 0
     */

        private int borrowId;
        private int borrowUserId;
        private long borrowTime;
        private int borrowState;
        private int bookId;

        @Override
        public String toString() {
            return "BorrowInfo{" +
                    "borrowId=" + borrowId +
                    ", borrowUserId=" + borrowUserId +
                    ", borrowTime=" + borrowTime +
                    ", borrowState=" + borrowState +
                    ", bookId=" + bookId +
                    '}';
        }

        public int getBorrowId() {
            return borrowId;
        }

        public void setBorrowId(int borrowId) {
            this.borrowId = borrowId;
        }

        public int getBorrowUserId() {
            return borrowUserId;
        }

        public void setBorrowUserId(int borrowUserId) {
            this.borrowUserId = borrowUserId;
        }

        public long getBorrowTime() {
            return borrowTime;
        }

        public void setBorrowTime(long borrowTime) {
            this.borrowTime = borrowTime;
        }

        public int getBorrowState() {
            return borrowState;
        }

        public void setBorrowState(int borrowState) {
            this.borrowState = borrowState;
        }

        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }
    }
}

