@startuml MapSaveFlow
actor User as "사용자"
participant Frontend as "AngularJS Frontend"
participant MapController as "Controller\n(MapController)"
participant MapService as "Service\n(MapService)"
participant MapRepository as "Repository\n(MapRepository)"
participant StorageService as "StorageService\n(S3)"
database DB as "MySQL Database"

User -> Frontend : "지도 저장 버튼 클릭"
note right of Frontend
  - 요청: { mapName, mapData, userId }
end note

Frontend -> MapController : POST /api/maps\n{ mapName, mapData, userId }
note right of MapController
  - @PostMapping("/api/maps")
  - createMap(data) 호출
end note

MapController -> MapService : createMap(data)
note right of MapService
  - 새로운 Map 엔티티 생성
  - MapRepository.save() 호출
end note

MapService -> MapRepository : save(Map entity)
MapRepository -> DB : INSERT INTO maps(...)
DB --> MapRepository : { savedMap }
MapRepository --> MapService : { savedMap }

note right of MapService
  - 외부 저장소 사용 시
    StorageService.upload(mapData) 호출
end note

MapService -> StorageService : upload(mapData)
StorageService --> MapService : 업로드 결과

MapService --> MapController : 200 OK { savedMap }
MapController --> Frontend : 200 OK { savedMap }
note right of Frontend
  - “지도 저장 완료” 알림 표시
  - 저장된 지도 목록 갱신
end note
@enduml
