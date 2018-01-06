//
//  CustomError.swift
//  Photosphere
//
//  Created by Soham Ray on 04/01/18.
//  Copyright © 2018 TheSphereHouse. All rights reserved.
//
//"error": {
//    "code": 0,
//    "message": "No errors"
//},
import Foundation
import ObjectMapper

struct CustomError : Mappable {
    
    var code: Int!
    var message: String!
    
    init?(map: Map) {
        
    }
    
    mutating func mapping(map: Map) {
        self.code <- map["code"]
        self.message <- map["message"]
    }
}
