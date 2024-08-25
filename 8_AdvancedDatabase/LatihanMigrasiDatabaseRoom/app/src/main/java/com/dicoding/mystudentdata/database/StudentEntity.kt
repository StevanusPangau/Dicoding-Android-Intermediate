package com.dicoding.mystudentdata.database

import androidx.room.*

@Entity
data class Student(
    @PrimaryKey
    val studentId: Int,
    val name: String,
    val univId: Int,
    @ColumnInfo(defaultValue = "false")
    val isGraduate: Boolean? = false
)

@Entity
data class University(
    @PrimaryKey
    val universityId: Int,
    val name: String,
)

@Entity
data class Course(
    @PrimaryKey
    val courseId: Int,
    val name: String,
)

// Relasi : Many to One (Banyak Student ke 1 University)
data class StudentAndUniversity(
    @Embedded
    val student: Student,
    @Relation(
        parentColumn = "univId",
        entityColumn = "universityId"
    )
    val university: University? = null,
)

// Relasi : One to Many (1 University ke Banyak Student)
data class UniversityAndStudent(
    @Embedded
    val university: University,
    @Relation(
        parentColumn = "universityId",
        entityColumn = "univId"
    )
    val student: List<Student>,
)

// Relasi : Many to Many (Banyak Student ke Banyak Course)
@Entity(primaryKeys = ["sId", "cId"])
data class CourseStudentCrossRef(
    val sId: Int,
    @ColumnInfo(index = true)
    val cId: Int,
)

data class StudentWithCourse(
    @Embedded
    val studentAndUniversity: StudentAndUniversity,
//    val student: Student,

    @Relation(
        parentColumn = "studentId",
        entity = Course::class,
        entityColumn = "courseId",
        associateBy = Junction(
            value = CourseStudentCrossRef::class,
            parentColumn = "sId",
            entityColumn = "cId"
        )
    )
    val course: List<Course>,
)