package com.dicoding.mystudentdata

import androidx.lifecycle.LiveData
import com.dicoding.mystudentdata.database.Student
import com.dicoding.mystudentdata.database.StudentAndUniversity
import com.dicoding.mystudentdata.database.StudentDao
import com.dicoding.mystudentdata.database.StudentWithCourse
import com.dicoding.mystudentdata.database.UniversityAndStudent
import com.dicoding.mystudentdata.helper.InitialDataSource
import com.dicoding.mystudentdata.helper.SortType
import com.dicoding.mystudentdata.helper.SortUtils

class StudentRepository(private val studentDao: StudentDao) {
    fun getAllStudent(sortType: SortType): LiveData<List<Student>> {
        val query = SortUtils.getSortedQuery(sortType)
        return studentDao.getAllStudent(query)
    }
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = studentDao.getAllStudentAndUniversity() // Relasi : Many to One (Banyak Student ke 1 University)
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = studentDao.getAllUniversityAndStudent() // Relasi : One to Many (1 University ke Banyak Student)
    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = studentDao.getAllStudentWithCourse() // Relasi : Many to Many (Banyak Student ke Banyak Course)
}