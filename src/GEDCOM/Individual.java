package GEDCOM;

import java.util.GregorianCalendar;

public class Individual {
        private String id;
        private String name;
        private char gender;
        private GregorianCalendar birthday;
        private int age;
        private boolean alive;
        private GregorianCalendar death;
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

        public char getGender() {
            return gender;
        }
        public void setGender(char gender) {
            this.gender = gender;
        }

        public GregorianCalendar getBirthday() {
            return birthday;
        }
        public void setBirthday(GregorianCalendar birthday) {
            this.birthday = birthday;
        }

        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }

        public boolean isAlive() {
            return alive;
        }
        public void setAlive(boolean alive) {
            this.alive = alive;
        }

        public GregorianCalendar getDeath() {
            return death;
        }
        public void setDeath(GregorianCalendar death) {
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



	
