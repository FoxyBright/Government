package com.study.government.model.appeals

enum class AppealsQuestion(
    val question: String,
    val organization: String,
    val address: String,
    val phones: List<String>,
    val email: String
) {
    CRIME_IN_REGION(
        phones = listOf("+74842502375", "+74842502459"),
        organization = "Управление МВД",
        address = "ул. Суворова, 139, Калуга",
        question = "Преступность в регионе",
        email = "mvd@mail.ru"
    ),
    HOW_TO_JOIN_THE_DUMA(
        question = "Вступление в думу",
        organization = "Правительство Москвы",
        address = "Тверская ул., 13, Москва • подъезд 5",
        phones = listOf("+74956921703"),
        email = "mos@gmail.com"
    ),
    HOW_TO_BECOME_PRESIDENT(
        question = "Становление президентом",
        organization = "Правительство Москвы",
        address = "Тверская ул., 13, Москва • подъезд 5",
        phones = listOf("+74956921703"),
        email = "mos@gmail.com"
    ),
    WHY_ARE_TAXES(
        question = "Налоги в регионе",
        organization = "Министерство финансов РФ",
        address = "ул. Ильинка, 9, стр. 1, Москва",
        phones = listOf("+7 (495) 913-11-11"),
        email = "minfin.gov@yandex.ru"
    ),
    OBTAINING_DRIVER_LICENSE(
        question = "Преступность в регионе",
        organization = "Управление ГИБДД ГУ МВД",
        address = "МВД России по Калужской области",
        phones = listOf(
            "+7 (495) 692-17-03",
            "+7 (495) 777-77-77",
            "+7 (495) 957-04-44",
            "+7 (495) 692-16-94"
        ),
        email = "ogibdd@gmail.ru"
    )
}