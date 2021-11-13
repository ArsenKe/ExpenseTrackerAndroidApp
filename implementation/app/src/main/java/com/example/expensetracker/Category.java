

import java.util.HashSet;
import java.util.Set;

public class Category {

    private int id;
    private String name;
    private boolean deleted;
    private User User;
    private final Set<TransactionType> validTransactionTypes;
    private final Set<AccountType> validAccountTypes;

    public Category(String name){
        validTransactionTypes = new HashSet<>();
        validAccountTypes = new HashSet<>();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public User getUser() {
        return User;
    }

    public Set<TransactionType> getValidTransactionTypes() {
        return validTransactionTypes;
    }

    public Set<AccountType> getValidAccountTypes() {
        return validAccountTypes;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setName(String name) {
        if (name == null){
            throw new IllegalArgumentException("Could not set name of category, because it was null.");
        }
        if (name.length() == 0) {
            throw new IllegalArgumentException("Could not set name of category, because length of name was 0.");
        }
        this.name = name;
    }

    public void addValidTransactionType(TransactionType transactionType) {
        validTransactionTypes.add(transactionType);
    }

    public void addValidAccountType(AccountType accountType) {
        validAccountTypes.add(accountType);
    }

    public void removeValidTransactionType(TransactionType transactionType) {
        validTransactionTypes.remove(transactionType);
    }

    public void removeValidAccountType(AccountType accountType) {
        validAccountTypes.remove(accountType);
    }
}