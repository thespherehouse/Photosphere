//
//  AppDelegate.swift
//  Photosphere
//
//  Created by Soham Ray on 27/12/17.
//  Copyright © 2017 TheSphereHouse. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        let _ = ServerConfig(config: .DEV)
        AuthenticationServices.shared.login(withEmail: "abcd@gmail.com", andPassword: "password123",completion: {(status,error) in
        })
        return true
    }



}

