//
//  GenericResponseModel.swift
//  Photosphere
//
//  Created by Soham Ray on 06/01/18.
//  Copyright © 2018 TheSphereHouse. All rights reserved.
//

import Foundation
import ObjectMapper

struct GenericResponse<T: Mappable> : Mappable {
    var error : CustomError!
    var data : T?
    
    init?(map: Map) {
        
    }
    
    mutating func mapping(map: Map) {
        self.error <- map["error"]
        self.data <- map["data"]
    }
}
