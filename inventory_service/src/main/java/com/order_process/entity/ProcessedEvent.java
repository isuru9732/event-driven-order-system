package com.order_process.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "processed_events")
public class ProcessedEvent {

	@Id
	@Column(name = "event_id")
	private UUID eventId;

	@CurrentTimestamp
	@Column(name = "processed_at")
	private Instant processedAt;
}
