package com.dicoding.mystudentdata.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: List<Student>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUniversity(university: List<University>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(course: List<Course>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourseStudentCrossRef(courseStudentCrossRef: List<CourseStudentCrossRef>)

//    @Query("SELECT * from student")
    @RawQuery(observedEntities = [Student::class])
//    fun getAllStudent(query: SupportSQLiteQuery): LiveData<List<Student>>
    fun getAllStudent(query: SupportSQLiteQuery): DataSource.Factory<Int, Student>

    // ======================== Relasi ========================

    // Relasi : Many to One (Banyak Student ke 1 University)
    @Transaction
    @Query("SELECT * from student")
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>>

    // Relasi : One to Many (1 University ke Banyak Student)
    @Transaction
    @Query("SELECT * from university")
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>>

    // Relasi : Many to Many (Banyak Student ke Banyak Course)
    @Transaction
    @Query("SELECT * from student")
    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>>

}