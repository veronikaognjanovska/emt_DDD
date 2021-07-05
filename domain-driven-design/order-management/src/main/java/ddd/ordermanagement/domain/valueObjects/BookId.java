package ddd.ordermanagement.domain.valueObjects;


import ddd.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class BookId extends DomainObjectId {

    private BookId() {
        super(BookId.randomId(BookId.class).getId());
    }

    public BookId(String uuid) {
        super(uuid);
    }
}
