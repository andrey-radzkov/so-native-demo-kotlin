package com.example.andrey_radzkov.model

import java.util.ArrayList

fun getArticles(): List<Article> {
    val articles = ArrayList<Article>()
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2018/05/ILA-Berlin-2018_A380.jpg",
            "ILA Berlin 2018: Innovation and Leadership in Aerospace",
            "https://www.supplyon.com/en/blog/ila-berlin-2018/",
            "“Innovation and Leadership in Aerospace” – this was the motto of this year’s aviation trade fair in Berlin from 25 to 29 April. "))
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2018/02/Material-Visibility-TT-blog.jpg",
            "Good for land and sea – How to ensure real-time visibility for production materials",
            "https://www.supplyon.com/en/blog/track-trace-for-production-materials-in-real-time/",
            "There are plenty of Track & Trace solutions (T&T) available. Logistics Service Providers (LSP) have been offering it as an add-on for years and several start-ups focus on this aspect, too. So it’s nothing new, right? Or is it?"))
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2017/11/Sensor-Clouds_header_blog_EN.jpg",
            "Increasing supply reliability and quality with Sensor Clouds",
            "https://www.supplyon.com/en/blog/increasing-supplier-reliability-quality-via-sensor-clouds/",
            "A leading aerospace supplier has further optimized its inbound supply chain with SupplyOn. As part of an innovative industry 4.0 project, sensor tracking was used to implement the real-time monitoring of deliveries. This not only aims at continuously determining location, but also the early detection of quality defects during transport due to excessive temperatures or moisture."))
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2017/11/Indirect-procurement_blog.jpg",
            "Indirect Procurement: The only way is up!",
            "https://www.supplyon.com/en/blog/indirect-procurement-way-up/",
            "At many companies, process optimization in purchasing focuses on direct material. The purchase of indirect material, however, often still takes place under the radar. Perhaps a bit overstated as; What is there to optimize, if I only purchase pencils?"))
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2017/11/BME-Symposium_blog2.jpg",
            "The network(ing) effect at the 52nd BME Purchasing and Logistics Symposium",
            "https://www.supplyon.com/en/blog/well-connected-bme-symposium/",
            "“Added value: global networks,” was the topic of the 52nd BME Purchasing and Logistics Symposium last week in Berlin from November 8th to 10th in Berlin."))
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2017/07/Conti-China-SupplyOn_warehouse-collaboration.jpg",
            "How Continental China optimized its warehouse collaboration with SupplyOn",
            "https://www.supplyon.com/en/blog/continental-china-optimizes-warehouse-collaboration/",
            "Using SupplyOn, Continental China could finally ensure a transparent collaboration with its 50 external warehouse service providers, thus optimizing internal and external processes. "))
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2017/11/6-Best-Practice-Steps_blog.jpg",
            "Six best practice steps for maintaining tax compliance",
            "https://www.supplyon.com/en/blog/six-best-practice-steps-for-maintaining-tax-compliance/",
            "Does the e-business solution that supports your business-to-business transactions ensure that your e-invoices remain tax compliant, everywhere, always?"))
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2017/11/WelcomeAddress_blog.jpg",
            "Aviation Forum: Advancing the digital value chain in a dynamic world",
            "https://www.supplyon.com/en/blog/aviation-forum-2017-advancing-digital-value-chain/",
            "The location and the timing has changed – but the excellent setup prevailed: this year, the 7th Aviation Forum took off at the beginning of November – this time on the grounds of the Hamburg Trade Fair."))
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2017/10/Ready-for-Takeoff_blog.jpg",
            "Ready for takeoff? Supply chain challenges in the aerospace industry",
            "https://www.supplyon.com/en/blog/supply-chain-challenges-aerospace-suppliers/",
            "The aerospace industry has a larger international footprint than almost any other sector. Ranging from individual connectors to the completed aircraft, the supply chain often spans the entire globe. In times of ever-closer collaboration and ever-tighter deadlines, the level of digitalization should actually be extensive. Actuall."))
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2017/07/Track-and-trace_blog.jpg",
            "Maximize your supply chain transparency with track and trace",
            "https://www.supplyon.com/en/blog/maximizing-supply-chain-transparency-with-track-and-trace/",
            "Track and trace, that is, determining the location of shipments, is definitely nothing new. Yet everyone still seems to be talking about it. How come?"))
    articles.add(Article("https://www.supplyon.com/wp-content/uploads/2017/09/Digitalisierung-kein-Selbstzweck_blog.jpg",
            "Digitalization is not an end in itself. A commentary",
            "https://www.supplyon.com/en/blog/digitalization-not-end-in-itself/",
            "Wherever you look, “digital” is THE overriding theme. Digitalization is not particularly new and by no means simply a future scenario for the years 2020 to 2030."))

    return articles
}