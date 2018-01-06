//
//  ServerConfig.swift
//  Photosphere
//
//  Created by Soham Ray on 31/12/17.
//  Copyright © 2017 TheSphereHouse. All rights reserved.
//

import Foundation


enum Config {
    case PROD
    case DEV
}

struct ServerConfig {
    static var baseURL = ""
    
    init(config: Config) {
        
        switch config {
        case .PROD:
            ServerConfig.baseURL = "http://www.thespherehouse.xyz/photosphere"
        case .DEV:
            ServerConfig.baseURL = "http://www.thespherehouse.xyz/photosphere"
        }
    }
}

struct CommonHeader {
    static let shared = CommonHeader()
    var deviceToken : String {
        if let devToken = KeychainManager.shared.getDeviceToken() {
            return devToken
        } else {
            print("-----------Device Token-------------\n\(UUID().uuidString)")
            KeychainManager.shared.keychain.set(UUID().uuidString, forKey: KeychainConstants.deviceToken)
            return UUID().uuidString
        }
    }
}
