package GEDCOM;

import java.time.LocalDate;
import java.time.Period;
import java.util.GregorianCalendar;

public class Individual {
        private String id;
        private String name;
        private String gender;
        private LocalDate birthday;
        private int age;
        private boolean alive;
        private LocalDate death;
        private String child;
        private String spouse;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }
        public void setGender(String gender) {
            this.gender = gender;
        }

        public LocalDate getBirthday() {
            return birthday;
        }
        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }

        public int getAge() {
        	LocalDate endDate = this.death !=null ? this.death : LocalDate.now();
        	return Period.between(birthday, endDate).getYears();
        }
        public void setAge(int age) {
            this.age = age;
        }

        public boolean isAlive() {
        	return this.death == null;
        }
        public void setAlive(boolean alive) {
            this.alive = alive;
        }

        public LocalDate getDeath() {
            return death;
        }
        public void setDeath(LocalDate death) {
            this.death = death;
        }

        public String getChild() {
            return child;
        }
        public void setChild(String child) {
            this.child = child;
        }

        public String getSpouse() {
            return spouse;
        }
        public void setSpouse(String spouse) {
            this.spouse = spouse;
        }
    }



	
