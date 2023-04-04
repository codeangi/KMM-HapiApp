//
//  CommonModels.swift
//  iosApp
//
//  Created by Swasthik K S on 30/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

struct TypeModel: Hashable {
    var typeIcon: String
    var typeName: String
}

struct MonthModel: Hashable {
    var month: String
    var year: String
    var dayAndTimeMap: [DateModel]
}

struct DateModel: Hashable {
    var weekDay: String
    var weekDate: String
    var time: [String]
}
