package com.study.government.model

import kotlin.random.Random

data class Servant(
    val id: Long = Random.nextLong(),
    val description: String,
    val department: String,
    val avatarUrl: String,
    val merits: String,
    val name: String,
    val post: String
)

val servantsPresets = listOf(
    Servant(
        avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2a/%D0%9C%D0%B8%D1%85%D0%B0%D0%B8%D0%BB_%D0%9C%D0%B8%D1%88%D1%83%D1%81%D1%82%D0%B8%D0%BD_%2830-03-2022%29_%28cropped%29.jpg",
        name = "Михаил Владимирович Мишустин",
        post = "Председатель Правительства Российской Федерации",
        department = "Правительство",
        description = "    Российский государственный и политический деятель, экономист. Председатель Правительства Российской Федерации с 16 января 2020 года. Член Государственного Совета Российской Федерации с 21 декабря 2020 года." +
                "\n    Руководитель Федерального агентства по управлению особыми экономическими зонами (2006—2008). Руководитель Федеральной налоговой службы Российской Федерации с 6 апреля 2010 по 16 января 2020 года." +
                "\n    Председатель Правительственной комиссии по контролю за осуществлением иностранных инвестиций в Российской Федерации." +
                "\n    Действительный государственный советник Российской Федерации 1-го класса, доктор экономических наук (2010)." +
                "\n    С марта 2022 года находится под персональными санкциями Евросоюза, Канады, Великобритании и Новой Зеландии, с 6 апреля — под персональными санкциями США, в мае к санкциям присоединилась Япония и ряд других стран.",
        merits = "• Орден Почёта (29 декабря 2012) — за достигнутые трудовые успехи и многолетнюю добросовестную работу." +
                "\n\n" +
                "• Почётная грамота Президента Российской Федерации (15 ноября 2013) — за достигнутые трудовые успехи, многолетнюю добросовестную работу и активную общественную деятельность." +
                "\n\n" +
                "• Орден «За заслуги перед Отечеством» IV степени (16 июля 2015) — за достигнутые трудовые успехи, активную общественную деятельность и многолетнюю добросовестную работу."
    ),
    Servant(
        avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/Vladimir_Kolokoltsev_%282020-02-21%29.jpg/656px-Vladimir_Kolokoltsev_%282020-02-21%29.jpg?20200221142310",
        name = "Владимир Александрович Колокольцев",
        post = "Министр внутренних дел Российской Федерации",
        department = "МВД РФ",
        description = "    Советский и российский деятель органов внутренних дел, юрист, российский государственный деятель. Министр внутренних дел Российской Федерации с 21 мая 2012 (исполняющий обязанности с 8 по 18 мая 2018 и с 15 по 21 января 2020).\n\n    " +
                "Начальник главного управления внутренних дел Москвы с 7 сентября 2009 по 24 марта 2011 года. Начальник главного управления МВД России по Москве с 24 марта 2011 по 21 мая 2012 года. Генерал полиции Российской Федерации (2015). Заслуженный сотрудник органов внутренних дел Российской Федерации. Доктор юридических наук (2005).\n\n    " +
                "Находится под международными санкциями Евросоюза, США, Великобритании и ряда других стран.",
        merits = "• Орден «За заслуги перед Отечеством» III и IV степеней." +
                "\n\n" +
                "• Заслуженный сотрудник органов внутренних дел Российской Федерации." +
                "\n\n" +
                "• Медаль «За отличие в охране общественного порядка»." +
                "\n\n" +
                "• Орден Александра Невского."
    ),
    Servant(
        avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/%D0%A1%D0%B5%D1%80%D0%B3%D0%B5%D0%B9_%D0%9B%D0%B0%D0%B2%D1%80%D0%BE%D0%B2_%2818-11-2022%29_%28cropped%29.jpg/375px-%D0%A1%D0%B5%D1%80%D0%B3%D0%B5%D0%B9_%D0%9B%D0%B0%D0%B2%D1%80%D0%BE%D0%B2_%2818-11-2022%29_%28cropped%29.jpg",
        name = "Сергей Викторович Лавров",
        post = "Министр иностранных дел Российской Федерации",
        department = "МИД РФ",
        description = "    Российский государственный и политический деятель, советский и российский дипломат. Министр иностранных дел Российской Федерации с 9 марта 2004 (и. о. 7—12 мая 2004, 8—12 мая 2008, 8—21 мая 2012, 8—18 мая 2018, 15—21 января 2020).\n\n    " +
                "Постоянный член Совета Безопасности Российской Федерации. Чрезвычайный и полномочный посол. Заслуженный работник дипломатической службы Российской Федерации (2004). Полный кавалер ордена «За заслуги перед Отечеством». Герой Труда Российской Федерации (2020).\n\n    " +
                "С 2022 года из-за вторжения России на Украину находится под персональными санкциями США, Евросоюза, Великобритании, Канады, Австралии, Японии и других стран",
        merits = "• Герой Труда Российской Федерации (17 марта 2020) — за особые трудовые заслуги перед государством и народом. Полный кавалер ордена «За заслуги перед Отечеством»." +
                "\n\n" +
                "• Орден «За заслуги перед Отечеством» I степени (21 марта 2015) — за особо выдающиеся заслуги в реализации внешнеполитического курса Российской Федерации и многолетнюю дипломатическую деятельность." +
                "\n\n" +
                "• Орден «За заслуги перед Отечеством» II степени (2010)." +
                "\n\n" +
                "• Орден «За заслуги перед Отечеством» III степени (21 марта 2005) — за большие заслуги в реализации внешнеполитического курса Российской Федерации и многолетнюю плодотворную работу." +
                "\n\n" +
                "• Орден «За заслуги перед Отечеством» IV степени (12 мая 1998) — за большой вклад в проведение внешнеполитического курса России и многолетнюю добросовестную работу."
    ),
    Servant(
        avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Sergey_Kravtsov_12.03.2020.jpg/375px-Sergey_Kravtsov_12.03.2020.jpg",
        name = "Сергей Сергеевич Кравцов",
        post = "Министр просвещения Российской Федерации",
        department = "Минпросвящения РФ",
        description = "    Российский государственный деятель. Министр просвещения Российской Федерации с 21 января 2020 года.\n\n    " +
                "Доктор педагогических наук (2007), доцент. Действительный государственный советник Российской Федерации 3 класса (2013).\n\n    " +
                "Руководитель Федеральной службы по надзору в сфере образования и науки (Рособрнадзор) с 8 августа 2013 года по 21 января 2020 года.\n\n   " +
                "Член Генерального совета партии «Единая Россия» с 4 декабря 2021 года.\n\n    " +
                "Находится под санкциями 27 стран Евросоюза, США, Великобритании и ряда других стран.",
        merits = "• Благодарность Правительства Российской Федерации (6 апреля 2016 года) — «за существенный вклад в развитие системы оценки качества отечественного образования, совершенствование механизмов проведения объективного единого государственного экзамена»." +
                "\n\n" +
                "• Звание «Почётный работник высшего профессионального образования Российской Федерации»." +
                "\n\n" +
                "• Почетная грамота Министерства образования и науки Российской Федерации." +
                "\n\n" +
                "• Медаль «За честь и мужество» (Кемеровская область; 2015)." +
                "\n\n" +
                "• Премия Федерации еврейских общин России «Скрипач на крыше» в номинации «Государственный деятель» (2021) — «за поддержку национально-культурных традиций народов России в общеобразовательной системе»." +
                "\n\n" +
                "• Благодарность партии «Единая Россия» (2023) — «за эффективное содействие в реализации народной программы партии „Единая Россия“»." +
                "\n\n" +
                "• Памятный знак «350 лет Петру Великому» (Санкт-Петербург; 2023)."
    ),
    Servant(
        avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/Olga_Liubimova_%282020-12-07%29.jpg/300px-Olga_Liubimova_%282020-12-07%29.jpg",
        name = "Ольга Борисовна Любимова",
        post = "Министр культуры Российской Федерации",
        department = "Минкультуры РФ",
        description = "    Российский государственный деятель, журналист и театровед. Министр культуры Российской Федерации с 21 января 2020 года.",
        merits = "•  Отсутствуют"
    ),
    Servant(
        avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Mikhail_Murashko_%282020-01-29%29.jpg/411px-Mikhail_Murashko_%282020-01-29%29.jpg",
        name = "Михаил Альбертович Мурашко",
        post = "Министр здравоохранения Российской Федерации",
        department = "Минздрав РФ",
        description = "    Российский государственный деятель, врач-гинеколог. Министр здравоохранения Российской Федерации с 21 января 2020 года. Доктор медицинских наук.\n\n    " +
                "Действительный государственный советник Российской Федерации 2-го класса.\n\n    " +
                "Находится под персональными международными санкциями ряда стран.",
        merits = "• Медаль ордена «За заслуги перед Отечеством» II степени (2018)." +
                "\n\n" +
                "• Командор со звездой ордена Заслуг (2021, Венгрия)." +
                "\n\n" +
                "• Благодарность Правительства Российской Федерации (2018)." +
                "\n\n" +
                "• Нагрудный знак «Отличник здравоохранения» (2014)." +
                "\n\n" +
                "• 2 Почётные грамоты Министерства здравоохранения Российской Федерации (2001, 2006)." +
                "\n\n" +
                "• Медаль «За содействие органам наркоконтроля» (2016)." +
                "\n\n" +
                "• Медаль «За боевое содружество» (2016)." +
                "\n\n" +
                "• Медаль «За заслуги перед отечественным здравоохранением» (2016)." +
                "\n\n" +
                "• Медаль «За заслуги перед Чеченской Республикой» (2017)." +
                "\n\n" +
                "• Юбилейная медаль «45 лет 3 отдел управления „П“ СЭБ ФСБ России» (2018)." +
                "\n\n" +
                "• Памятная медаль «XXII Олимпийские зимние игры и XI Паралимпийские зимние игры 2014 года в г. Сочи» (2014)." +
                "\n\n" +
                "• памятная юбилейная медаль МВД России «100 лет международному полицейскому сотрудничеству» (2015)." +
                "\n\n" +
                "• памятный знак «Медицинская служба уголовно-исполнительной системы» (2019)." +
                "\n\n" +
                "• грамота Союза машиностроителей России (2019)." +
                "\n\n" +
                "• Благодарность Министерства здравоохранения Российской Федерации." +
                "\n\n" +
                "• Благодарность ФСБ России (2019)." +
                "\n\n" +
                "• Почётная грамота Республики Ингушетия (2016)." +
                "\n\n" +
                "• Почётная грамота Республики Коми (2004)."
    ),
    Servant(
        avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Anton_Siluanov_%282019-09-25%29.jpg/315px-Anton_Siluanov_%282019-09-25%29.jpg",
        name = "Антон Германович Силуанов",
        post = "Министр финансов Российской Федерации",
        department = "Минфин РФ",
        description = "    Российский государственный деятель, экономист. Министр финансов Российской Федерации с 2011 года, член Совета безопасности Российской Федерации (с 2011 года). " +
                "Заместитель председателя правительства Российской Федерации с 2018 по 2020 год, " +
                "министр финансов Российской Федерации. Действительный государственный советник Российской Федерации 1-го класса (2008). " +
                "Доктор экономических наук (2012). Член Всероссийской политической партии «Единая Россия».\n\n    " +
                "Находится под персональными международными санкциями США, Канады, Австралии, Украины, Новой Зеландии.",
        merits = "• Орден «За заслуги перед Отечеством» III степени (15 апреля 2013 года) — за большой вклад в " +
                "проведение государственной финансовой политики и многолетнюю добросовестную работу." +
                "\n\n" +
                "• Орден «За заслуги перед Отечеством» IV степени (2 февраля 2011 года) — за достигнутые трудовые успехи и многолетнюю добросовестную работу." +
                "\n\n" +
                "• Медаль ордена «За заслуги перед Отечеством» I степени (13 апреля 2007) — за заслуги в области экономики и финансовой деятельности." +
                "\n\n" +
                "• Почётная грамота Президента Российской Федерации (18 ноября 2010) — за своевременное обеспечение ввода в " +
                "обращение заграничных паспортов, содержащих электронные носители информации." +
                "\n\n" +
                "• Благодарность Президента Российской Федерации (1 сентября 2012) — за заслуги в области экономики, " +
                "финансовой деятельности и многолетнюю добросовестную работу." +
                "\n\n" +
                "• Благодарность Президента Российской Федерации (6 сентября 2002) — за заслуги в области экономики и финансовой деятельности." +
                "\n\n" +
                "• Почётная грамота Министерства финансов Российской Федерации (2001 год)." +
                "\n\n" +
                "• Благодарность Министра финансов Российской Федерации (2002 год)." +
                "\n\n" +
                "• Орден «Полярная Звезда» (Якутия)." +
                "\n\n" +
                "• Орден «Дуслык» (Татарстан, 2015 год)." +
                "\n\n" +
                "• Медаль «За заслуги перед Чеченской Республикой» (24 ноября 2008) — за значительный вклад в проведение государственной " +
                "финансовой политики в Чеченской Республике." +
                "\n\n" +
                "• Медаль «За заслуги перед Республикой Карелия» (8 июня 2020) — за заслуги перед Республикой Карелия и её жителями, " +
                "большой вклад в социально-экономическое развитие республики и активную работу в составе." +
                "\n\n" +
                "• Государственной комиссии по подготовке к празднованию 100-летия образования Республикой Карелия."
    ),
    Servant(
        avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/Official_portrait_of_Sergey_Shoigu.jpg/345px-Official_portrait_of_Sergey_Shoigu.jpg",
        name = "Сергей Кужугетович Шойгу",
        post = "Министр обороны Российской Федерации",
        department = "Минобороны РФ",
        description = "    Советский и российский государственный и военный деятель. Министр обороны Российской Федерации с 6 ноября 2012 года. Генерал армии (2003). Герой Российской Федерации (1999). Заслуженный спасатель Российской Федерации (2000).\n\n    " +
                "Кавалер ордена Святого апостола Андрея Первозванного с мечами (2014). Член Высшего совета политической партии «Единая Россия», член Совета Безопасности РФ с 1996 (Постоянный член с 2012).\n\n    " +
                "Председатель Государственного комитета РСФСР и Российской Федерации по делам гражданской обороны, чрезвычайным ситуациям и ликвидации последствий стихийных бедствий (1991—1994), глава МЧС России (1994—2012), губернатор Московской области (2012).\n\n    " +
                "Глава межрегионального движения «Единство» (1999—2001), основатель (1 декабря 2001 года) и сопредседатель партии «Единая Россия» (2001—2002, вместе с Юрием Лужковым и Минтимером Шаймиевым)\n\n    " +
                "На данный момент единственный министр, который с небольшим перерывом является членом Правительства Российской Федерации с 1990-х годов (в общей сложности — 30 лет).\n\n    " +
                "Президент организации «Русское географическое общество» (с 2009).\n\n    " +
                "С 2022 года находится под персональными санкциями Евросоюза, США, Новой Зеландии и ряда других стран.",
        merits = "• Орден «За заслуги перед Отечеством» I степени с мечами (21 мая 2020)." +
                "\n\n" +
                "• Орден Святого апостола Андрея Первозванного с мечами за отличия в боевых действиях (2014)." +
                "\n\n" +
                "• Орден Александра Невского (2014)." +
                "\n\n" +
                "• Орден «За заслуги перед Отечеством» II степени (28 декабря 2010) — за заслуги перед " +
                "государством и многолетний добросовестный труд." +
                "\n\n" +
                "• Орден Почёта (2009) — за заслуги перед государством и большой вклад в совершенствование системы " +
                "обеспечения безопасности Российской Федерации в области гражданской обороны, защиты населения " +
                "и территорий от чрезвычайных ситуаций." +
                "\n\n" +
                "• Орден «За заслуги перед Отечеством» III степени (21 мая 2005) — за большой вклад в укрепление " +
                "гражданской обороны и заслуги в предотвращении и ликвидации последствий стихийных бедствий." +
                "\n\n" +
                "• Заслуженный спасатель Российской Федерации (18 мая 2000) — за заслуги в предотвращении " +
                "и ликвидации последствий аварий, катастроф и стихийных бедствий." +
                "\n\n" +
                "• Герой Российской Федерации — за мужество и героизм, проявленные при" +
                " исполнении воинского долга в экстремальных ситуациях (20 сентября 1999)." +
                "\n\n" +
                "• Орден «За личное мужество» (февраль 1994)." +
                "\n\n" +
                "• Медаль «Защитнику свободной России» (март 1993)."
    ),
    Servant(
        avatarUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d8/Konstantin_Chuychenko_official_portrait.png/300px-Konstantin_Chuychenko_official_portrait.png",
        name = "Константин Анатольевич Чуйченко",
        post = "Министр юстиции Российской Федерации",
        department = "Минюст РФ",
        description = "    Российский государственный деятель. Министр юстиции Российской Федерации с 21 января 2020 года. Член Совета безопасности Российской Федерации с 3 февраля 2020 года. Действительный государственный советник юстиции Российской Федерации (2020).\n\n    " +
                "Помощник президента Российской Федерации — начальник Контрольного управления президента Российской Федерации (2008—2018). Заместитель председателя правительства Российской Федерации — руководитель Аппарата правительства Российской Федерации (2018—2020).\n\n    " +
                "Из-за поддержки российско-украинской войны — под санкциями 27 стран ЕС, Великобритании, США, Канады, Швейцарии, Австралии, Украины, Новой Зеландии.",
        merits = "• Орден «За заслуги перед Отечеством» IV степени (3 августа 2011 года) — за большие заслуги " +
                "в обеспечении деятельности Президента Российской Федерации и многолетнюю плодотворную работу." +
                "\n\n" +
                "• Орден Почёта (23 марта 2006 года) — за достигнутые трудовые успехи и многолетнюю добросовестную работу." +
                "\n\n" +
                "• Орден Александра Невского (21 марта 2014 года)." +
                "\n\n" +
                "• Орден преподобного Сергия Радонежского II степени (РПЦ, 2024)."
    )
)