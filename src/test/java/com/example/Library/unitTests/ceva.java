package com.example.Library.unitTests;

public class ceva {

    //  @Scheduled(cron = "0 0 0 * * *") // every day at 00:00
    //  @Scheduled(cron = "*/50 * * * * *") //every 50 seconds
    //  @Scheduled(cron = "0 */50 * * * *") //every 50 minutes
    /*
    second (0-59)
         | minute (0-59)
         | | hour (0-23)
         | | | day of month (1-31)
         | | | | month (1-12 or JAN-DEC)
         | | | | | day of week (0-6 or SUN-SAT)
         | | | | | |
    cron = "0 0 0 * * *" - specifies when a method must be triggered;

    Available attributes:
     1. targetIdentity() -> | @OneToOne | @OneToMany | @ManyToOne | @ManyToMany |
     2. cascade()        -> | @OneToOne | @OneToMany | @ManyToOne | @ManyToMany |
     3. fetch()          -> | @OneToOne | @OneToMany | @ManyToOne | @ManyToMany |
     4. optional()       -> | @OneToOne |            | @ManyToOne |             |
     5. mappedBy()       -> | @OneToOne | @OneToMany |            | @ManyToMany |

     persist = save child when save parent

    //to dto
     dto.setBooks(author.getBooks().stream()
                .map(BookMapper::toSimpleBookDto)
                .collect(Collectors.toList()));

    //to entity
    if (dto.getBooks() != null) {
            dto.getBooks().forEach(bookDto -> {
                Book bookEntity = BookMapper.toEntity(bookDto);
                author.addBook(bookEntity);
            });
        }
        orphan removal
        cand relatia dintre parinte si copil nu mai exista sa se stearga copilul
        cascade delete e ca sterg parinte se sterge si copilul

    */

}
